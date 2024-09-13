import { ref, watch } from 'vue'

import { useMutation } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { RegisterStudent, RegisterStudentForm } from '@/interfaces'

const createRegister = async (register: RegisterStudentForm) => {
  const response = await fetchWrapper.post<RegisterStudentForm, RegisterStudent>(
    '/v1/register-student/create/base64',
    register
  )

  return response
}

export const useRegisterStudent = (idRegister: string) => {
  const register = ref<RegisterStudent | undefined>(undefined)

  const mutation = useMutation({
    mutationFn: createRegister
  })

  watch(mutation.isSuccess, (success) => {
    if (success) {
      register.value = mutation.data.value!
    }
  })

  return {
    register,
    create(photo: string) {
      mutation.mutate({
        idRegister,
        photo
      })
    }
  }
}
