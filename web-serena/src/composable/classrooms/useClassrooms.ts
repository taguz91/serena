import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Classroom, Paginate } from '@/interfaces'
import { useClassroomsStore } from '@/stores/admin/classrooms'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { computed, watch } from 'vue'

const getClassrooms = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Classroom>>(`/v1/classroom?page=${page}`)

  return data
}

const deleteClassroom = async (id: string) => {
  await fetchWrapper.delete(`/v1/classroom/${id}`)
}

export const useClassrooms = () => {
  const store = useClassroomsStore()

  const { currentPage, classrooms, metaData } = storeToRefs(store)

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['classrooms?page=', currentPage],
    queryFn: () => getClassrooms(currentPage.value - 1)
  })

  const mutationDelete = useMutation({
    mutationFn: deleteClassroom
  })

  watch(data, () => {
    if (data.value) {
      store.setClassrooms(data.value.data)
    }
  })

  watch(mutationDelete.isSuccess, (isSuccess) => {
    if (isSuccess) {
      mutationDelete.reset()
      refetch()
    }
  })

  return {
    isLoading,
    classrooms,
    currentPage,
    metaData: computed(() => metaData.value),

    // actions
    getPage(page: number) {
      store.setPage(page)
    },
    deleteClassroom(id: string) {
      mutationDelete.mutate(id)
    }
  }
}
