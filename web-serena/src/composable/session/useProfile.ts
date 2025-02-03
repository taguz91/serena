import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { ProfileForm, Teacher } from '@/interfaces'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { ref, watch } from 'vue'

const getSession = () => {
  const data = fetchWrapper.get<unknown, Teacher>('/v1/session/profile')

  return data
}

const updateSession = (request: ProfileForm) => {
  const data = fetchWrapper.put<ProfileForm, Teacher>('/v1/session/update', request)

  return data
}

export const useSessionProfile = () => {
  const { isLoading, data } = useQuery({
    queryKey: ['session', 'profile'],
    queryFn: () => getSession()
  })

  const form = ref<ProfileForm>({})

  watch(data, () => {
    if (data.value) {
      form.value = {
        name: data.value.name,
        email: data.value.email
      }
    }
  })

  return {
    isLoading,
    form,
    session: data
  }
}

export const useUpdateSessionProfile = () => {
  const updateMutation = useMutation({
    mutationFn: updateSession
  })

  watch(updateMutation.isSuccess, (success) => {
    if (success) {
      updateMutation.reset()
    }
  })

  return {
    update(profile: ProfileForm) {
      updateMutation.mutate(profile)
    }
  }
}
