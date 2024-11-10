import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Classroom, ClassroomForm } from '@/interfaces'
import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query'
import type { FormInst } from 'naive-ui'
import { ref, watch, type Ref } from 'vue'

const getClassroom = async (id?: string) => {
  if (!id) {
    return null
  }

  const data = await fetchWrapper.get<unknown, Classroom>(`/v1/classroom/${id}`)

  return data
}

const saveClassroom = async (classroom: ClassroomForm) => {
  const data = await fetchWrapper.post<ClassroomForm, Classroom>(`/v1/classroom`, classroom)

  return data
}

const updateClassroom = async (classroom: ClassroomForm) => {
  const data = await fetchWrapper.put<ClassroomForm, Classroom>(
    `/v1/classroom/${classroom.id}`,
    classroom
  )

  return data
}

const emptyForm = (): ClassroomForm => ({
  idAcademicPeriod: '',
  idSubject: '',
  idTeacher: '',
  name: ''
})

export const useClassroom = (id: Ref<string | string[] | undefined>) => {
  const classroom = ref<Classroom | null>(null)
  const classroomForm = ref<ClassroomForm>(emptyForm())

  const queryClient = useQueryClient()
  const { isLoading, data } = useQuery({
    queryKey: ['classroom', id],
    queryFn: () => getClassroom(id.value?.toString())
  })

  const updateMutation = useMutation({
    mutationFn: updateClassroom
  })
  const saveMutation = useMutation({
    mutationFn: saveClassroom
  })

  watch(data, () => {
    if (data.value) {
      //
      classroom.value = {
        ...data.value
      }
      classroomForm.value = {
        id: data.value.id,
        idAcademicPeriod: data.value.academicPeriod.id,
        idSubject: data.value.subject.id,
        idTeacher: data.value.teacher.id,
        name: data.value.name
      }
    } else {
      classroomForm.value = emptyForm()
    }
  })

  watch([saveMutation.isSuccess, updateMutation.isSuccess], (isSave, isUpdate) => {
    if (isSave || isUpdate) {
      const queries = queryClient.getQueryCache().findAll({
        queryKey: ['classrooms?page='],
        exact: false
      })

      queries.forEach((query) => {
        query.fetch()
      })

      classroomForm.value = emptyForm()

      saveMutation.reset()
      updateMutation.reset()
    }
  })

  return {
    classroom,
    classroomForm,
    isLoading,

    // actions
    save(formRef: FormInst | null) {
      if (!formRef) {
        return
      }

      formRef.validate((errors) => {
        if (errors) {
          // show errors
          return
        }

        if (id.value) {
          updateMutation.mutate(classroomForm.value)
        } else {
          saveMutation.mutate(classroomForm.value)
        }
      })
    }
  }
}
