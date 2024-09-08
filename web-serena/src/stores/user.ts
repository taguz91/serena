import { defineStore } from 'pinia'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Teacher } from '@/interfaces'
import router from '@/router'
import { delay } from '@/utils'

export const useAuthStore = defineStore({
  id: 'auth',
  state: () =>
    ({
      user: null,
      returnUrl: null
    }) as {
      user: Teacher | null
      returnUrl: string | null
    },
  actions: {
    async init(): Promise<Teacher | undefined> {
      await delay(1000)

      const localUser = localStorage.getItem('user')

      if (localUser) {
        this.user = JSON.parse(localUser)
        await delay(1000)

        return this.user ?? undefined
      }
    },
    async login(email: string, password: string) {
      try {
        const user = await fetchWrapper.post(`/v1/login`, { email, password })
        this.user = user

        // changes this to use more secure storage
        localStorage.setItem('user', JSON.stringify(user))
        router.push(this.returnUrl || '/')
      } catch (error) {
        // const message = useMessage()
        if (error instanceof Error) {
          console.log(error.message)
          // message.error(error.message)
        } else {
          console.log('Usuario o contraseña incorrectos')
          // message.error('Usuario o contraseña incorrectos')
        }
      }
    },
    logout() {
      this.user = null
      localStorage.removeItem('user')
      router.push('/')
    }
  },
  getters: {
    isLoggedIn(): boolean {
      return this.user ? !!this.user?.token : false
    }
  }
})
