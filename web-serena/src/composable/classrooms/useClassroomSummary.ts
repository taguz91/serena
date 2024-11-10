import { ref, watch, type Ref } from 'vue'
import { useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Summary } from '@/interfaces'
import { useAuthStore } from '@/stores/user'

const getSummary = async (id: string, academicPeriods: string) => {
  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/classroom/summary/${id}?periods=${academicPeriods}`
  )

  return data
}

export const useClassroomSummary = (id: Ref<string | string[]>) => {
  const summary = ref<Summary[]>([])
  const { sessionInfo } = useAuthStore()

  const { isLoading, data } = useQuery({
    queryKey: ['classroom-summary?id=', id],
    queryFn: () => getSummary(id.value.toString(), sessionInfo?.academicPeriods.join(',') || '')
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
