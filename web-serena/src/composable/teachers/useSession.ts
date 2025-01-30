import { fetchWrapper } from '@/helpers/fetch_wrapper'
import { useMutation } from '@tanstack/vue-query'
import { watch } from 'vue'

const updatePasswordApi = async (password: string) => {
  const record = await fetchWrapper.put<Record<string, any>, Record<string, any>>(
    '/v1/session/update',
    {
      password
    }
  )

  return record
}

export const useUpdateSession = (onSuccess: () => void) => {
  const updatePasswordMutation = useMutation({
    mutationFn: updatePasswordApi
  })

  const updatePassword = async (password: string) => {
    updatePasswordMutation.mutate(password)
  }

  watch(updatePasswordMutation.isSuccess, (success) => {
    if (success) {
      onSuccess()
    }
  })

  return {
    updatePassword
  }
}
