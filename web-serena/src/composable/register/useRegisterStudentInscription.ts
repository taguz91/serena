import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { RegisterStudent, Student } from '@/interfaces'
import { useQuery } from '@tanstack/vue-query'
import { ref, watch, type Ref } from 'vue'

const byIdentification = async (identification: string) => {
  const response = await fetchWrapper.get<unknown, Student>(
    `/v1/student/identification/${identification}`
  )

  return response
}

export const useRegisterStudentInscription = (identification: Ref<{ identification: string }>) => {
  const student = ref<Student | undefined>(undefined)

  const { isLoading, data, refetch } = useQuery({
    enabled: false,
    retry: 0,
    queryKey: ['student', 'identification', identification],
    queryFn: () => byIdentification(identification.value.identification)
  })

  watch(data, () => {
    if (data.value) {
      student.value = data.value
    }
  })

  return {
    isLoading,
    student,

    // actions
    async search() {
      const res = await refetch()

      return res.status
    }
  }
}

const byRegisterIdStudent = async (idRegister: string, idStudent: string) => {
  const response = await fetchWrapper.get<unknown, RegisterStudent>(
    `/v1/register-student/exists/inscription/${idRegister}/${idStudent}`
  )

  return response
}

export const useExistsRegisterStudentInscription = (
  idRegister: string,
  student: Ref<Student | undefined>
) => {
  const registerStudent = ref<RegisterStudent | undefined>(undefined)

  const { data, refetch } = useQuery({
    enabled: false,
    retry: 0,
    queryKey: ['exists', 'inscription', idRegister, student],
    queryFn: () => byRegisterIdStudent(idRegister, student.value?.id || '')
  })

  watch(data, () => {
    if (data.value) {
      registerStudent.value = data.value
    }
  })

  return {
    registerStudent,

    // actions
    async search() {
      const res = await refetch()

      return res.status
    }
  }
}
