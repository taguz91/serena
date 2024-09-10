import { ref, watch, type Ref } from 'vue'

import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query'
import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Subject, SubjectForm } from '@/interfaces'
import type { FormInst } from 'naive-ui'

const getSubject = async (id?: string) => {
  if (!id) {
    return null
  }
  const data = await fetchWrapper.get<unknown, Subject>(`/v1/subject/${id}`)

  return data
}

const saveSubject = async (subject: SubjectForm) => {
  const data = await fetchWrapper.post<SubjectForm, Subject>('/v1/subject', subject)

  return data
}

const updateSubject = async (subject: SubjectForm) => {
  const data = await fetchWrapper.put<SubjectForm, Subject>(`/v1/subject/${subject.id}`, subject)

  return data
}

export const useSubject = (id: Ref<string | undefined>) => {
  const subject = ref<Subject>()
  const subjectForm = ref<SubjectForm>({
    name: ''
  })

  const queryClient = useQueryClient()

  const { isLoading, data } = useQuery({
    queryKey: ['subject', id],
    queryFn: () => getSubject(id.value)
  })

  const mutationSave = useMutation({
    mutationFn: saveSubject
  })
  const mutationUpdate = useMutation({
    mutationFn: updateSubject
  })

  watch(data, () => {
    if (data.value) {
      subject.value = { ...data.value }
      subjectForm.value = {
        id: data.value.id,
        name: data.value.name
      }
    } else {
      subjectForm.value = {
        name: ''
      }
    }
  })

  watch([mutationSave.isSuccess, mutationUpdate.isSuccess], (isSave, isUpdate) => {
    if (isSave || isUpdate) {
      const queries = queryClient.getQueryCache().findAll({
        queryKey: ['subjects?page='],
        exact: false
      })

      queries.forEach((query) => {
        query.fetch()
      })

      subjectForm.value = {
        name: ''
      }
    }
  })

  return {
    isLoading,
    subject,
    subjectForm,

    // actions,
    save(formRef: FormInst | null) {
      if (!formRef) {
        return
      }

      formRef.validate().then(() => {
        if (subjectForm.value.id) {
          mutationUpdate.mutate(subjectForm.value)
        } else {
          mutationSave.mutate(subjectForm.value)
        }
      })
    }
  }
}
