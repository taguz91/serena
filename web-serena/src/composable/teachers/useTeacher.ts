import { ref, watch, type Ref } from 'vue'

import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Teacher, TeacherForm } from '@/interfaces'
import type { FormInst } from 'naive-ui'

const getTeacher = async (id: string | undefined): Promise<Teacher | null> => {
  if (!id) {
    return null
  }

  const teacher = await fetchWrapper.get<unknown, Teacher>(`/v1/teacher/${id}`)

  return teacher
}

const saveTeacher = async (data: TeacherForm) => {
  const record = await fetchWrapper.post<TeacherForm, Teacher>('/v1/teacher', data)

  return record
}

const updateTeacher = async (data: TeacherForm) => {
  const record = await fetchWrapper.put<TeacherForm, Teacher>(`/v1/teacher/${data.id}`, data)

  return record
}

export const useTeacher = (id: Ref<string | string[] | undefined>) => {
  const teacher = ref<Teacher>()
  const teacherForm = ref<TeacherForm>({
    name: '',
    email: '',
    isAdmin: false
  })

  const { isLoading, data } = useQuery({
    queryKey: ['teacher', id],
    queryFn: () => getTeacher(id.value?.toString())
  })

  const queryClient = useQueryClient()

  watch(
    data,
    () => {
      if (data.value) {
        teacher.value = {
          ...data.value
        }

        teacherForm.value = {
          id: data.value.id,
          name: data.value.name,
          email: data.value.email,
          isAdmin: data.value.isAdmin ?? false
        }
      } else {
        teacherForm.value = {
          name: '',
          email: '',
          isAdmin: false
        }
      }
    },
    { immediate: true }
  )

  const saveMutation = useMutation({
    mutationFn: saveTeacher
  })

  const updateMutation = useMutation({
    mutationFn: updateTeacher
  })

  const save = async (formRef: FormInst | null) => {
    formRef?.validate((errors) => {
      if (errors) {
        console.log('show errors', errors)
        return
      }

      if (id.value) {
        updateMutation.mutate(teacherForm.value)
      } else {
        saveMutation.mutate(teacherForm.value)
      }
    })
  }

  watch([saveMutation.isSuccess, updateMutation.isSuccess], (isSave, isUpdate) => {
    if (isSave || isUpdate) {
      const queries = queryClient.getQueryCache().findAll({
        queryKey: ['teachers?page='],
        exact: false
      })

      queries.forEach((query) => {
        query.fetch()
      })

      teacherForm.value = {
        name: '',
        email: '',
        isAdmin: false
      }

      saveMutation.reset()
      updateMutation.reset()
    }
  })

  return {
    isLoading,
    teacher,
    teacherForm,
    save
  }
}
