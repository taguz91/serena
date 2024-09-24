import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Student } from '@/interfaces'
import { useAdminStudentStore } from '@/stores/admin/students'

import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'

const getStudents = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Student>>(`/v1/student?page=${page}`)

  return data
}

export const useStudentsAll = () => {
  const store = useAdminStudentStore()
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
