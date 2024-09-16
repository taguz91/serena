import { storeToRefs } from 'pinia'

import { useRegisterStudentsStore } from '@/stores/app/register-students'
import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { RegisterStudent } from '@/interfaces'
import { useQuery } from '@tanstack/vue-query'
import { watch } from 'vue'

const getStudents = async (idRegister: string) => {
  const data = await fetchWrapper.get<unknown, RegisterStudent[]>(
    `/v1/register-student/register/current/${idRegister}`
  )

  return data
}

export const useRegisterStudents = (idRegister: string) => {
  const store = useRegisterStudentsStore()

  const { students } = storeToRefs(store)

  const { isLoading, data } = useQuery({
    queryKey: ['register-students', idRegister],
    queryFn: () => getStudents(idRegister)
  })

  watch(data, () => {
    if (data.value) {
      store.setStudents(data.value)
    }
  })

  return {
    isLoading,
    students

    // actions
  }
}
