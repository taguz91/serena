<template>
  <div class="mb-2 flex justify-end">
    <div class="w-[300px]">
      <NInput placeholder="Buscar" v-model:value="search" @update-value="onSearch" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useDebounceFn } from '@vueuse/core'
import { NInput } from 'naive-ui'
import { ref } from 'vue'

const search = ref<string | undefined>(undefined)

const onSearch = useDebounceFn(() => {
  console.log('search', search.value)
  emit('search', search.value)
}, 1000)

const emit = defineEmits<{
  (event: 'search', value: string | undefined): void
}>()
</script>
