<template>
  <div class="mb-2 flex justify-end">
    <div class="w-[300px]">
      <NInput placeholder="Buscar" v-model:value="searchInput" @update-value="onSearch" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useDebounceFn } from '@vueuse/core'
import { NInput } from 'naive-ui'
import { ref } from 'vue'

const props = defineProps<{
  search: string | undefined
}>()

const searchInput = ref<string | undefined>(props.search)

const onSearch = useDebounceFn(() => {
  emit('search', searchInput.value)
}, 1000)

const emit = defineEmits<{
  (event: 'search', value: string | undefined): void
}>()
</script>
