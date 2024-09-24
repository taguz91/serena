import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Register } from '@/interfaces'
import { useAdminRegisterStore } from '@/stores/admin/registers'

import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'

const getRegisters = async (page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Register>>(`/v1/register?page=${page}`)

  return data
}

export const useRegisterAll = () => {
  const store = useAdminRegisterStore()
  const { currentPage, registers, metaData } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['registers?page=', currentPage],
    queryFn: () => getRegisters(currentPage.value - 1)
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

    getPage(page: number) {
      store.setPage(page)
    }
  }
}
