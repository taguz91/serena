import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Methodology, MethodologyEmotion, MethodologyForm } from '@/interfaces'
import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query'
import type { FormInst } from 'naive-ui'
import { ref, watch, type Ref } from 'vue'

const getMethodology = async (id: string | undefined) => {
  if (!id) {
    return null
  }

  const data = await fetchWrapper.get<unknown, Methodology>(`/v1/methodology/${id}`)

  return data
}

const saveMethodology = async (data: MethodologyForm) => {
  const record = await fetchWrapper.post<MethodologyForm, Methodology>(`/v1/methodology`, data)

  return record
}

const editMethodology = async (data: MethodologyForm) => {
  const record = await fetchWrapper.put<MethodologyForm, Methodology>(
    `/v1/methodology/${data.id}`,
    data
  )

  return record
}

export const useMethodology = (id: Ref<string | undefined>) => {
  const methodology = ref<Methodology>()
  const methodologyForm = ref<MethodologyForm>({
    name: '',
    summary: '',
    emotions: []
  })

  const { isLoading, data } = useQuery({
    queryKey: ['methodology', id],
    queryFn: () => getMethodology(id.value)
  })

  watch(data, () => {
    if (data.value) {
      methodology.value = {
        ...data.value
      }

      methodologyForm.value = {
        id: data.value.id,
        name: data.value.name,
        summary: data.value.summary,
        emotions: data.value.emotions.map((e: MethodologyEmotion) => e.emotion)
      }
    } else {
      methodologyForm.value = {
        name: '',
        summary: '',
        emotions: []
      }
    }
  })

  const queryClient = useQueryClient()

  const saveMutation = useMutation({
    mutationFn: saveMethodology
  })

  const updateMutation = useMutation({
    mutationFn: editMethodology
  })

  const save = async (formRef: FormInst | null) => {
    if (!formRef?.validate()) return

    if (methodologyForm.value.id) {
      updateMutation.mutate(methodologyForm.value)
    } else {
      saveMutation.mutate(methodologyForm.value)
    }
  }

  watch([saveMutation.isSuccess, updateMutation.isSuccess], (isSave, isUpdate) => {
    if (isSave || isUpdate) {
      const queries = queryClient.getQueryCache().findAll({
        queryKey: ['methodologies?page='],
        exact: false
      })

      queries.forEach((query) => {
        query.fetch()
      })
    }
  })

  return {
    methodology,
    methodologyForm,

    isLoading,
    save
  }
}
