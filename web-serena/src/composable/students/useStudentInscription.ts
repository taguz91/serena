import { ref, watch } from 'vue'
import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Student, StudentForm } from '@/interfaces'
import { useMutation } from '@tanstack/vue-query'
import { useRouter } from 'vue-router'

const saveStudent = async (student: StudentForm) => {
  const data = await fetchWrapper.post<StudentForm, Student>('/v1/student', student)

  return data
}

export const useStudentInscription = (idRegister: string) => {
  const router = useRouter()
  const isCreating = ref<boolean>(false)

  const studentForm = ref<StudentForm>({
    name: '',
    identification: '',
    gender: ''
  })

  const mutationSave = useMutation({
    mutationFn: saveStudent
  })

  watch(mutationSave.isSuccess, (success) => {
    if (success) {
      studentForm.value = {
        name: '',
        identification: '',
        gender: ''
      }

      mutationSave.data.value?.id

      router.push({
        name: 'public-register-inscription-photo',
        params: {
          id: idRegister,
          idStudent: mutationSave.data.value?.id
        }
      })
    }

    isCreating.value = false
  })

  return {
    studentForm,
    isCreating,
    save() {
      isCreating.value = true
      mutationSave.mutate(studentForm.value)
    }
  }
}
