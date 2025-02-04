<template>
  <button
    type="button"
    class="px-4 py-2 ml-auto border-2 border-slate-900 hover:bg-black hover:text-white font-semibold rounded-xl"
    @click.stop="(event) => $emit('click', event)"
  >
    {{ loading ? '...' : label }}
  </button>
</template>

<script setup lang="ts">
interface Props {
  loading?: boolean
  label: string
}

withDefaults(defineProps<Props>(), {
  loading: false
})

defineEmits<{
  (event: 'click', e: MouseEvent): void
}>()
</script>
