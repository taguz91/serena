import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Career, CareerForm } from '@/interfaces'
import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query'
import type { FormInst } from 'naive-ui'
import { ref, watch, type Ref } from 'vue'

const getCareer = async (id: string | undefined) => {
  if (!id) {
    return null
  }

  const career = await fetchWrapper.get<unknown, Career>(`/v1/carrera/${id}`)

  return career
}

const saveCareer = async (data: CareerForm) => {
  const record = await fetchWrapper.post<CareerForm, Career>(`/v1/carrera`, data)

  return record
}

const editCareer = async (data: CareerForm) => {
  const record = await fetchWrapper.put<CareerForm, Career>(`/v1/carrera/${data.id}`, data)

  return record
}

export const useCareer = (id: Ref<string | undefined>) => {
  const career = ref<Career>()
  const careerForm = ref<CareerForm>({
    name: '',
    description: ''
  })

  const { isLoading, data } = useQuery({
    queryKey: ['career', id],
    queryFn: () => getCareer(id.value)
  })

  watch(data, () => {
    if (data.value) {
      career.value = {
        ...data.value
      }

      careerForm.value = {
        id: data.value.id,
        name: data.value.name,
        description: data.value.description
      }
    } else {
      careerForm.value = {
        name: '',
        description: ''
      }
    }
  })

  const queryClient = useQueryClient()

  const saveMutation = useMutation({
    mutationFn: saveCareer
  })

  const updateMutation = useMutation({
    mutationFn: editCareer
  })

  const save = async (formRef: FormInst | null) => {
    formRef?.validate((errors) => {
      if (errors) {
        console.log('show errors', errors)
        return
      }

      if (id.value) {
        updateMutation.mutate(careerForm.value)
      } else {
        saveMutation.mutate(careerForm.value)
      }
    })
  }

  watch([saveMutation.isSuccess, updateMutation.isSuccess], (isSave, isUpdate) => {
    if (isSave || isUpdate) {
      const queries = queryClient.getQueryCache().findAll({
        queryKey: ['careers?page='],
        exact: false
      })

      queries.forEach((query) => {
        query.fetch()
      })
    }
  })

  return {
    career,
    careerForm,

    //
    isLoading,
    save
  }
}
