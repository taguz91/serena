import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Option, OptionType } from '@/interfaces'
import { useOptionStore } from '@/stores/admin/options'
import { useQuery } from '@tanstack/vue-query'
import { storeToRefs } from 'pinia'
import { watch } from 'vue'
import { computed } from 'vue'

const getOptions = async (type: OptionType) => {
  const data = await fetchWrapper.get<unknown, Option[]>(`/v1/${type}/all/options`)

  return data
}

export const useOptions = (type: OptionType) => {
  const store = useOptionStore()
  const { options } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['options', type],
    queryFn: () => getOptions(type)
  })

  watch(data, () => {
    if (data.value) {
      store.setOptions(type, data.value)
    }
  })

  return {
    isLoading,
    options: computed(() => options.value[type])
  }
}
