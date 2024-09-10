import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Register, RegisterForm } from '@/interfaces'
import { useMutation, useQuery } from '@tanstack/vue-query'
import { useMessage } from 'naive-ui'
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'

const getRegister = async (id: string) => {
  const data = await fetchWrapper.get<unknown, Register>(`/v1/register/${id}`)

  return data
}

const createRegister = async (register: RegisterForm) => {
  const data = await fetchWrapper.post<RegisterForm, Register>('/v1/register', register)

  return data
}

export const useRegister = (id: string) => {
  const register = ref<Register | undefined>(undefined)

  const { isLoading, data } = useQuery({
    queryKey: ['register', id],
    queryFn: () => getRegister(id)
  })

  watch(data, () => {
    if (data.value) {
      register.value = data.value
    }
  })

  return {
    isLoading,
    register
  }
}

export const userCreateRegister = () => {
  const saveMutation = useMutation({
    mutationFn: createRegister
  })
  const router = useRouter()
  const message = useMessage()
  const idClassroomCreating = ref<string>('')

  watch(saveMutation.isSuccess, (success) => {
    if (success) {
      saveMutation.data.value?.id

      idClassroomCreating.value = ''
      router.push({
        name: 'classroom-register',
        params: {
          id: saveMutation.data.value?.id
        }
      })
    }
  })

  watch(saveMutation.isError, (error) => {
    if (error) {
      idClassroomCreating.value = ''
      message.error('No se puedo crear un registro de asistencia, vuelve a intentarlo.')
    }
  })

  return {
    idClassroomCreating,
    create(register: RegisterForm) {
      idClassroomCreating.value = register.idClassroom
      saveMutation.mutate(register)
    }
  }
}
