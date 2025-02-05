import { watch, ref, type Ref } from 'vue'
import { useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Methodology, Summary } from '@/interfaces'

const getSummary = async (id: string, academicPeriods: string) => {
  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/student/summary/${id}?periods=${academicPeriods}`
  )

  return data
}

const getMethodologies = async (emotions: string) => {
  const data = await fetchWrapper.get<unknown, Methodology[]>(
    `/v1/methodology/filter?emotions=${emotions}`
  )

  return data
}

export const useEmotionSummary = (id: Ref<string | string[]>, idAcademicPeriods: Ref<string[]>) => {
  const summary = ref<Summary[]>([])
  const methodologies = ref<Methodology[]>([])

  const { isLoading, data } = useQuery({
    queryKey: ['emotion-summary?id=', id, idAcademicPeriods],
    queryFn: () => getSummary(id.value.toString(), idAcademicPeriods.value.join(',') || '')
  })

  watch(data, async () => {
    if (data.value) {
      summary.value = data.value
      const response = await getMethodologies(data.value.map((item) => item.emotion).join(','))

      methodologies.value = response
    }
  })

  return {
    isLoading,
    summary,
    methodologies
  }
}
