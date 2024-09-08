import { storeToRefs } from 'pinia'

import { useAuthStore } from '@/stores/user'

export const useAunt = () => {
  const authStore = useAuthStore()

  const { user } = storeToRefs(authStore)

  return {
    user
  }
}
