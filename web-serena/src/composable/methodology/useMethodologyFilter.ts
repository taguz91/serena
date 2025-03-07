import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Methodology } from '@/interfaces'
import { useQuery } from '@tanstack/vue-query'
import { ref, watch, type ComputedRef } from 'vue'

const getMethodologies = async (emotions: string) => {
  if (emotions.length === 0) {
    return []
  }

  const data = await fetchWrapper.get<unknown, Methodology[]>(
    `/v1/methodology/filter?emotions=${emotions}`
  )

  return data
}

export const useMethodologyFilter = (emotions: ComputedRef<string>) => {
  const methodologies = ref<Methodology[]>([])

  const { isLoading, data } = useQuery({
    queryKey: ['methodologies-filter?emotions=', emotions],
    queryFn: () => getMethodologies(emotions.value)
  })

  watch(data, () => {
    if (data.value) {
      methodologies.value = data.value
    }
  })

  return {
    isLoading,
    methodologies
  }
}
