import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Student } from '@/interfaces'
import { useQuery } from '@tanstack/vue-query'
import { watch } from 'vue'
import { ref, type Ref } from 'vue'

const getStudent = async (id?: string) => {
  if (!id) {
    return null
  }

  const data = await fetchWrapper.get<unknown, Student>(`/v1/student/${id}`)

  return data
}

export const useStudent = (id: Ref<string | string[]>) => {
  const student = ref<Student | null>(null)

  const { isLoading, data } = useQuery({
    queryKey: ['student', id],
    queryFn: () => getStudent(id.value?.toString())
  })

  watch(data, () => {
    if (data.value) {
      student.value = {
        ...data.value
      }
    }
  })

  return {
    student,
    isLoading
  }
}
