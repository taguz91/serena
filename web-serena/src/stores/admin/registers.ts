import type { Meta, Register } from '@/interfaces'
import { defaultMeta } from '@/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAdminRegisterStore = defineStore('admin-registers', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)

  const registers = ref<Register[]>([])

  return {
    registers,
    metaData,
    currentPage,
    // actions

    setRegisters(newStudents: Register[]) {
      registers.value = newStudents
    },
    setMeta(meta: Meta) {
      metaData.value = { ...meta }
    },
    setPage(page: number) {
      if (page == currentPage.value) {
        return
      }

      currentPage.value = page
    }
  }
})
