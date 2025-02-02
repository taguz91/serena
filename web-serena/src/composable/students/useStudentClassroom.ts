import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Student } from '@/interfaces'
import { useStudentClassroomStore } from '@/stores/app/student-classroom'
import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch, type Ref } from 'vue'

const getStudents = async (id: string) => {
  const data = await fetchWrapper.get<unknown, Array<Student>>(`/v1/student/classroom/${id}`)

  return data
}

export const useStudentsClassroom = (id: Ref<string | string[]>) => {
  const store = useStudentClassroomStore()
  const { students, metaData } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['students-classroom?id=', id],
    queryFn: () => getStudents(id.value.toString())
  })

  watch(data, () => {
    if (data.value) {
      store.setStudents(data.value)
    }
  })

  return {
    isLoading,
    metaData,
    students
  }
}
