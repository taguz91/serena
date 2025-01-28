import type { Option, OptionType } from '@/interfaces'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useOptionStore = defineStore('options', () => {
  const options = ref<Record<OptionType, Option[]>>({
    'academic-period': [],
    subject: [],
    teacher: [],
    classroom: [],
    carrera: []
  })

  return {
    options,

    setOptions(type: OptionType, data: Option[]) {
      options.value[type] = data
    }
  }
})
