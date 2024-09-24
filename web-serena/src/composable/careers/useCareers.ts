import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Career, Paginate } from '@/interfaces'
import { useCareersStore } from '@/stores/admin/careers'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'
import { computed } from 'vue'

const getCareers = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Career>>(`/v1/carrera?page=${page}`)

  return data
}

const deleteCareer = async (id: string) => {
  await fetchWrapper.delete(`/v1/carrera/${id}`)
}

export const useCareers = () => {
  const store = useCareersStore()

  const { metaData, careers, currentPage } = storeToRefs(store)

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['careers?page=', currentPage],
    queryFn: () => getCareers(currentPage.value - 1)
  })

  const mutationDelete = useMutation({
    mutationFn: deleteCareer
  })

  watch(data, () => {
    if (data.value) {
      store.setMeta(data.value.meta)
      store.setCareers(data.value.data)
    }
  })

  watch(mutationDelete.isSuccess, () => {
    if (mutationDelete.isSuccess.value) {
      refetch
      mutationDelete.reset()
    }
  })

  return {
    isLoading,
    metaData: computed(() => metaData.value),
    careers,
    currentPage,

    // actions
    getPage(page: number) {
      store.setPage(page)
    },
    deleteCareer(id: string) {
      mutationDelete.mutate(id)
    }
  }
}
