import { watch } from 'vue'
import { storeToRefs } from 'pinia'

import { useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Classroom } from '@/interfaces'
import { useCurrentClassroomsStore } from '@/stores/app/classrooms'

const getCurrentClassrooms = async () => {
  const data = await fetchWrapper.get<unknown, Classroom[]>('/v1/classroom/teacher/current')

  return data
}

export const useCurrentClassrooms = () => {
  const store = useCurrentClassroomsStore()

  const { classrooms } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['current-classrooms'],
    queryFn: getCurrentClassrooms
  })

  watch(data, () => {
    if (data.value) {
      store.setClassrooms(data.value)
    }
  })

  return {
    isLoading,
    classrooms
  }
}
