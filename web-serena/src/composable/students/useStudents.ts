import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Student } from '@/interfaces'
import { useStudentStore } from '@/stores/app/students'
import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'

const getStudents = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Student>>(
    `/v1/student/teacher/current?page=${page}`
  )

  return data
}

export const useStudentsTeacher = () => {
  const store = useStudentStore()
  const { currentPage, students, metaData } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['students?page=', currentPage],
    queryFn: () => getStudents(currentPage.value - 1)
  })

  watch(data, () => {
    if (data.value) {
      store.setStudents(data.value.data)
      store.setMeta(data.value.meta)
    }
  })

  return {
    isLoading,
    metaData,
    currentPage,
    students,

    getPage(page: number) {
      store.setPage(page)
    }
  }
}
