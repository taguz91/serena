import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Methodology, Paginate } from '@/interfaces'
import { useMethodologyStore } from '@/stores/admin/methodologies'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { computed, watch } from 'vue'

const getMethodologies = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Methodology>>(
    `/v1/methodology?page=${page}`
  )

  return data
}

const deleteMethodology = async (id: string) => {
  await fetchWrapper.delete(`/v1/methodology/${id}`)
}

export const useMethodologies = () => {
  const store = useMethodologyStore()

  const { metaData, methodologies, currentPage } = storeToRefs(store)

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['methodologies?page=', currentPage],
    queryFn: () => getMethodologies(currentPage.value - 1)
  })

  const mutationDelete = useMutation({
    mutationFn: deleteMethodology
  })

  watch(data, () => {
    if (data.value) {
      store.setMeta(data.value.meta)
      store.setMethodologies(data.value.data)
    }
  })

  watch(mutationDelete.isSuccess, (isSuccess) => {
    if (isSuccess) {
      refetch()
      mutationDelete.reset()
    }
  })

  return {
    isLoading,
    metaData: computed(() => metaData.value),
    methodologies,
    currentPage,

    // actions
    getPage(page: number) {
      store.setPage(page)
    },
    deleteMethodology(id: string) {
      mutationDelete.mutate(id)
    }
  }
}
