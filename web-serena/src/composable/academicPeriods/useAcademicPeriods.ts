import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { AcademicPeriod, Paginate } from '@/interfaces'
import { useAcademicPeriodsStore } from '@/stores/admin/academic-periods'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { computed, watch } from 'vue'

const getAcademicPeriods = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<AcademicPeriod>>(
    `/v1/academic-period?page=${page}`
  )

  return data
}

const deleteAcademicPeriod = async (id: string) => {
  await fetchWrapper.delete(`/v1/academic-period/${id}`)
}

export const useAcademicPeriods = () => {
  const store = useAcademicPeriodsStore()

  const { metaData, academicPeriods, currentPage } = storeToRefs(store)

  const mutationDelete = useMutation({
    mutationFn: deleteAcademicPeriod
  })

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['academic-periods?page=', currentPage],
    queryFn: () => getAcademicPeriods(currentPage.value - 1)
  })

  watch(data, () => {
    if (data.value) {
      store.setMeta(data.value.meta)
      store.setAcademicPeriods(data.value.data)
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
    academicPeriods,
    currentPage,

    // actions
    getPage(page: number) {
      store.setPage(page)
    },
    deleteAcademicPeriod(id: string) {
      mutationDelete.mutate(id)
    }
  }
}
