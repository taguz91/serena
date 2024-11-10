import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { StudentSubject } from '@/interfaces'
import { useStudentSubjectSummary } from '@/stores/app/student-subject-summary'
import { useAuthStore } from '@/stores/user'
import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch, type Ref } from 'vue'

const getSubjects = async (id: string, academicPeriods: string) => {
  const data = await fetchWrapper.get<unknown, StudentSubject[]>(
    `/v1/student/subjects/${id}?periods=${academicPeriods}`
  )

  return data
}

export const useStudentSubjectsSummary = (id: Ref<string | string[]>) => {
  const store = useStudentSubjectSummary()
  const { sessionInfo } = useAuthStore()

  const { subjects } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['student-subjects?id=', id],
    queryFn: () => getSubjects(id.value.toString(), sessionInfo?.academicPeriods.join(',') || '')
  })

  watch(data, () => {
    if (data.value) {
      store.setSubjects(data.value)
    }
  })

  return {
    isLoading,
    subjects
  }
}
