import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Student } from '@/interfaces'
import { useQuery } from '@tanstack/vue-query'
import { ref, watch, type Ref } from 'vue'

const byIdentification = async (identification: string) => {
  const response = await fetchWrapper.get<unknown, Student>(
    `/v1/student/identification/${identification}`
  )

  return response
}

export const useRegisterStudentInscription = (identification: Ref<string>) => {
  const student = ref<Student | undefined>(undefined)

  const { isLoading, data, refetch } = useQuery({
    enabled: false,
    retry: 0,
    queryKey: ['student', 'identification', identification],
    queryFn: () => byIdentification(identification.value)
  })

  watch(data, () => {
    if (data.value) {
      student.value = data.value
    }
  })

  return {
    isLoading,
    student,

    // actions
    async search() {
      const res = await refetch()

      return res.status
    }
  }
}
