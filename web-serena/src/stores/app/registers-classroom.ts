import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { Meta, Register } from '@/interfaces'
import { defaultMeta } from '@/utils'

export const useRegistersClassroomStore = defineStore('registers-classroom', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)

  const registers = ref<Register[]>([])

  return {
    currentPage,
    metaData,
    registers,

    setRegisters(newRegisters: Register[]) {
      registers.value = newRegisters
    },

    setMeta(meta: Meta) {
      metaData.value = { ...meta }
    },

    setPage(page: number) {
      if (page === currentPage.value) {
        return
      }

      currentPage.value = page
    }
  }
})
