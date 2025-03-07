import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Career, Paginate } from '@/interfaces'
import { useCareersStore } from '@/stores/admin/careers'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'
import { computed } from 'vue'

const getCareers = async (page: number, search: string | undefined) => {
  let data
  if (search && search.length > 0) {
    data = await fetchWrapper.get<unknown, Paginate<Career>>(
      `/v1/carrera?page=${page}&search=description:${search}`
    )
  } else {
    data = await fetchWrapper.get<unknown, Paginate<Career>>(`/v1/carrera?page=${page}`)
  }

  return data
}

const deleteCareer = async (id: string) => {
  await fetchWrapper.delete(`/v1/carrera/${id}`)
}

export const useCareers = () => {
  const store = useCareersStore()

  const { metaData, careers, currentPage, search } = storeToRefs(store)

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['careers?page=', currentPage, search],
    queryFn: () => getCareers(currentPage.value - 1, search.value)
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
    search,

    // actions
    getPage(page: number) {
      store.setPage(page)
    },
    deleteCareer(id: string) {
      mutationDelete.mutate(id)
    },
    getSearch(search: string | undefined) {
      store.setSearch(search)
    }
  }
}
