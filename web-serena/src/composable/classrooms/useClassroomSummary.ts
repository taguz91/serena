import { ref, watch, type Ref } from 'vue'
import { useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Summary } from '@/interfaces'

const getSummary = async (id: string) => {
  const data = await fetchWrapper.get<unknown, Summary[]>(`/v1/classroom/summary/${id}`)

  return data
}

export const useClassroomSummary = (id: Ref<string | string[]>) => {
  const summary = ref<Summary[]>([])

  const { isLoading, data } = useQuery({
    queryKey: ['classroom-summary?id=', id],
    queryFn: () => getSummary(id.value.toString())
  })

  watch(data, () => {
    if (data.value) {
      summary.value = data.value
    }
  })

  return {
    isLoading,
    summary
  }
}
