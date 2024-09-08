import { useAuthStore } from '@/stores/user'
import { storeToRefs } from 'pinia'

export const useAunt = () => {
  const authStore = useAuthStore()

  const { user } = storeToRefs(authStore)

  return {
    user
  }
}
