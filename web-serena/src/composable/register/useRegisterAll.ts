import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Register } from '@/interfaces'
import { useAdminRegisterStore } from '@/stores/admin/registers'

import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'

const getRegisters = async (page: number, search: string | undefined) => {
  let data

  if (search && search.length > 0) {
    data = await fetchWrapper.get<unknown, Paginate<Register>>(
      `/v1/register?page=${page}&search=${search}`
    )
  } else {
    data = await fetchWrapper.get<unknown, Paginate<Register>>(`/v1/register?page=${page}`)
  }

  return data
}

export const useRegisterAll = () => {
  const store = useAdminRegisterStore()
  const { currentPage, registers, metaData, search } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['registers?page=', currentPage, search],
    queryFn: () => getRegisters(currentPage.value - 1, search.value)
  })

  watch(data, () => {
    if (data.value) {
      store.setRegisters(data.value.data)
      store.setMeta(data.value.meta)
    }
  })

  return {
    isLoading,
    metaData,
    currentPage,
    registers,
    search,

    getPage(page: number) {
      store.setPage(page)
    },
    getSearch(search: string | undefined) {
      store.setSearch(search)
    }
  }
}
