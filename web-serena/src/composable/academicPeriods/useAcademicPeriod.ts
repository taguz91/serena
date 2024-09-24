import { ref, watch, type Ref } from 'vue'

import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { AcademicPeriod, AcademicPeriodForm } from '@/interfaces'
import type { FormInst } from 'naive-ui'

const getAcademicPeriod = async (id: string | undefined) => {
  if (!id) {
    return null
  }

  const academicPeriod = await fetchWrapper.get<unknown, AcademicPeriod>(
    `/v1/academic-period/${id}`
  )

  return academicPeriod
}

const saveAcademicPeriod = async (data: AcademicPeriodForm) => {
  const record = await fetchWrapper.post<AcademicPeriodForm, AcademicPeriod>(
    '/v1/academic-period',
    data
  )

  return record
}

const updateAcademicPeriod = async (data: AcademicPeriodForm) => {
  const record = await fetchWrapper.put<AcademicPeriodForm, AcademicPeriod>(
    `/v1/academic-period/${data.id}`,
    data
  )

  return record
}

export const useAcademicPeriod = (id: Ref<string | undefined>) => {
  const academicPeriod = ref<AcademicPeriod>()
  const academicPeriodForm = ref<AcademicPeriodForm>({
    name: '',
    isActive: false,
    idCarrera: ''
  })

  const { isLoading, data } = useQuery({
    queryKey: ['academic-period', id],
    queryFn: () => getAcademicPeriod(id.value)
  })

  const queryClient = useQueryClient()

  watch(
    data,
    () => {
      if (data.value) {
        academicPeriod.value = {
          ...data.value
        }

        academicPeriodForm.value = {
          id: data.value.id,
          name: data.value.name,
          idCarrera: data.value.carrera?.id || '',
          isActive: data.value.isActive
        }
      } else {
        academicPeriodForm.value = {
          name: '',
          idCarrera: '',
          isActive: false
        }
      }
    },
    { immediate: true }
  )

  // save and edit logic

  const saveMutation = useMutation({
    mutationFn: saveAcademicPeriod
  })
  const updateMutation = useMutation({
    mutationFn: updateAcademicPeriod
  })

  const save = async (formRef: FormInst | null) => {
    formRef?.validate((errors) => {
      if (errors) {
        console.log('show errors', errors)
        return
      }

      if (id.value) {
        updateMutation.mutate(academicPeriodForm.value)
      } else {
        saveMutation.mutate(academicPeriodForm.value)
      }
    })
  }

  watch([saveMutation.isSuccess, updateMutation.isSuccess], (isSave, isUpdate) => {
    if (isSave || isUpdate) {
      const queries = queryClient.getQueryCache().findAll({
        queryKey: ['academic-periods?page='],
        exact: false
      })

      queries.forEach((query) => {
        query.fetch()
      })
    }
  })

  return {
    academicPeriod,
    academicPeriodForm,
    isLoading,
    save
  }
}
