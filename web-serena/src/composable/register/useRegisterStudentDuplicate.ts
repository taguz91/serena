import { ref, watch } from 'vue'

import { useMutation } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { RegisterStudent, RegisterStudentDuplicateForm } from '@/interfaces'

const createRegister = async (register: RegisterStudentDuplicateForm) => {
  const response = await fetchWrapper.post<RegisterStudentDuplicateForm, RegisterStudent>(
    '/v1/register-student/create/inscription',
    register
  )

  return response
}

export const useRegisterStudentDuplicate = (
  idRegister: string,
  onCreated: (student: RegisterStudent) => void
) => {
  const register = ref<RegisterStudent | undefined>(undefined)

  const mutation = useMutation({
    mutationFn: createRegister
  })

  watch(mutation.isSuccess, (success) => {
    if (success) {
      register.value = mutation.data.value!

      if (register.value) {
        onCreated(register.value)
      }
    }
  })

  return {
    register,

    // actions
    duplicate(idStudent: string) {
      mutation.mutate({
        idRegister,
        idStudent
      })
    },
    update(idStudent: string, photo: string) {
      mutation.mutate({
        idRegister,
        idStudent,
        photo
      })
    }
  }
}
