import { watch, ref, type Ref } from 'vue'
import { useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Summary } from '@/interfaces'

const getSummary = async (id: string, academicPeriods: string) => {
  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/teacher/summary/${id}?periods=${academicPeriods}`
  )

  return data
}

const getSummaryByDate = async (
  id: string,
  academicPeriods: string,
  start: string,
  end: string
) => {
  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/teacher/summary/${id}/${start}/${end}?periods=${academicPeriods}`
  )

  return data
}

export const useEmotionSummaryTeacher = (
  id: Ref<string | string[]>,
  idAcademicPeriods: Ref<string[]>
) => {
  const summary = ref<Summary[]>([])

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['emotion-summary?id=', id, idAcademicPeriods],
    queryFn: () => getSummary(id.value.toString(), idAcademicPeriods.value.join(',') || '')
  })

  watch(data, async () => {
    if (data.value) {
      summary.value = data.value
    }
  })

  const loadByDate = async (start: number, end: number) => {
    try {
      const startDate = new Date(start)
      const endDate = new Date(end)
      const data = await getSummaryByDate(
        id.value.toString(),
        idAcademicPeriods.value.join(',') || '',
        startDate.toISOString().split('T')[0],
        endDate.toISOString().split('T')[0]
      )

      summary.value = data

      summary.value = data
    } finally {
      // isLoading.value = false
    }
  }

  const reset = () => {
    refetch()
  }

  return {
    isLoading,
    summary,
    loadByDate,
    reset
  }
}
