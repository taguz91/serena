import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Register } from '@/interfaces'
import { useRegistersClassroomStore } from '@/stores/app/registers-classroom'
import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { computed, watch, type Ref } from 'vue'

const getRegisters = async (id: string, page: number) => {
  const data = await fetchWrapper.get<unknown, Paginate<Register>>(
    `/v1/register/classroom/${id}?page=${page}`
  )

  return data
}

export const useRegistersClassroom = (id: Ref<string | string[]>) => {
  const store = useRegistersClassroomStore()

  const { currentPage, registers, metaData } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['registers-classroom?page=', id, currentPage],
    queryFn: () => getRegisters(id.value.toString(), currentPage.value - 1)
  })

  watch(data, () => {
    if (data.value) {
      store.setRegisters(data.value.data)
      store.setMeta(data.value.meta)
    }
  })

  return {
    isLoading,
    registers,
    currentPage,
    metaData: computed(() => metaData.value),

    // actions
    getPage(page: number) {
      store.setPage(page)
    }
  }
}
