import { computed, watch } from 'vue'

import { useMutation, useQuery } from '@tanstack/vue-query'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Teacher } from '@/interfaces'
import { useTeachersStore } from '@/stores/admin/teachers'
import { storeToRefs } from 'pinia'

interface ChangePasswordProps {
  id: string
  password: string
}

const getTeachers = async (page: number): Promise<Paginate<Teacher>> => {
  const data = await fetchWrapper.get<unknown, Paginate<Teacher>>(`/v1/teacher?page=${page}`)

  return data
}

const deleteTeacher = async (id: string) => {
  await fetchWrapper.delete(`/v1/teacher/${id}`)
}

const activateTeacher = async (id: string) => {
  await fetchWrapper.put(`/v1/teacher/toggle/activate/${id}`)
}

const changePasswordApi = async (request: ChangePasswordProps) => {
  await fetchWrapper.put(`/v1/teacher/change/password/${request.id}`, {
    password: request.password
  })
}

export const useTeachers = () => {
  const store = useTeachersStore()
  const { metaData, teachers, currentPage } = storeToRefs(store)

  const mutationDelete = useMutation({
    mutationFn: deleteTeacher
  })

  const mutationActivate = useMutation({
    mutationFn: activateTeacher
  })

  const changePassword = useMutation({
    mutationFn: changePasswordApi
  })

  const { isLoading, data, refetch } = useQuery({
    queryKey: ['teachers?page=', currentPage],
    queryFn: () => getTeachers(currentPage.value - 1)
  })

  watch(data, (newData) => {
    if (newData) {
      store.setMeta(newData.meta)
      store.setTeachers(newData.data)
    }
  })

  watch(mutationDelete.isSuccess, () => {
    if (mutationDelete.isSuccess.value) {
      refetch()
      mutationDelete.reset()
    }
  })

  watch(changePassword.isSuccess, () => {
    if (changePassword.isSuccess.value) {
      changePassword.reset()
    }
  })

  watch(mutationActivate.isSuccess, () => {
    if (mutationActivate.isSuccess.value) {
      refetch()
      mutationActivate.reset()
    }
  })

  return {
    isLoading,
    metaData: computed(() => metaData.value),
    teachers,

    currentPage,

    // actions
    getPage(page: number) {
      store.setPage(page)
    },
    deleteTeacher(id: string) {
      mutationDelete.mutate(id)
    },
    activateTeacher(id: string) {
      mutationActivate.mutate(id)
    },
    updatePassword: (id: string, password: string) => {
      changePassword.mutate({ id, password })
    }
  }
}
