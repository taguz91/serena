import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Subject } from '@/interfaces'
import { useSubjectStore } from '@/stores/admin/subjects'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { computed, watch } from 'vue'

const getSubjects = async (page: number, search: string | undefined) => {
  let data
  if (search && search.length > 0) {
    data = await fetchWrapper.get<unknown, Paginate<Subject>>(
      `/v1/subject?page=${page}&search=name:${search}`
    )
  } else {
    data = await fetchWrapper.get<unknown, Paginate<Subject>>(`/v1/subject?page=${page}`)
  }

  return data
}

const deleteSubject = async (id: string) => {
  await fetchWrapper.delete(`v1/subject/${id}`)
}

export const useSubjects = () => {
  const store = useSubjectStore()

  const { currentPage, metaData, subjects, search } = storeToRefs(store)

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['subjects?page=', currentPage, search],
    queryFn: () => getSubjects(currentPage.value - 1, search.value)
  })

  watch(data, () => {
    if (data.value) {
      store.setSubjects(data.value.data)
      store.setMeta(data.value.meta)
    }
  })

  const mutationDelete = useMutation({
    mutationFn: deleteSubject
  })

  watch(mutationDelete.isSuccess, () => {
    if (mutationDelete.isSuccess.value) {
      refetch()
      mutationDelete.reset()
    }
  })

  return {
    isLoading,
    currentPage,
    subjects,
    metaData: computed(() => metaData.value),
    search,

    // actions
    deleteSubject(id: string) {
      mutationDelete.mutate(id)
    },
    getPage(page: number) {
      store.setPage(page)
    },
    getSearch(search: string | undefined) {
      store.setSearch(search)
    }
  }
}
