import { computed, watch } from 'vue'

import { useMutation, useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Teacher } from '@/interfaces'
import { useTeachersStore } from '@/stores/admin/teachers'
import { storeToRefs } from 'pinia'

const getTeachers = async (page: number): Promise<Paginate<Teacher>> => {
  const data = await fetchWrapper.get<unknown, Paginate<Teacher>>(`/v1/teacher?page=${page}`)

  return data
}

const deleteTeacher = async (id: string) => {
  await fetchWrapper.delete(`/v1/teacher/${id}`)
}

export const useTeachers = () => {
  const store = useTeachersStore()
  const { metaData, teachers, currentPage } = storeToRefs(store)

  const mutationDelete = useMutation({
    mutationFn: deleteTeacher
  })

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['teachers?page=', currentPage],
    queryFn: () => getTeachers(currentPage.value - 1)
  })

  watch(data, (newData) => {
    if (newData) {
      store.setMeta(newData.meta)
      store.setTeachers(newData.data)
    }
  })

  watch(mutationDelete.isSuccess, () => {
    if (mutationDelete.isSuccess.value) {
      refetch()
      mutationDelete.reset()
    }
  })

  return {
    isLoading,
    metaData: computed(() => metaData.value),
    teachers,

    currentPage,

    // actions
    getPage(page: number) {
      console.log('set the page number', page)
      store.setPage(page)
    },
    deleteTeacher(id: string) {
      mutationDelete.mutate(id)
    }
  }
}
