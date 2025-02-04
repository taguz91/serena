<template>
  <button
    type="button"
    class="px-4 py-2 ml-auto border-2 bg-black cursor-pointer text-white font-semibold rounded-xl hover:bg-slate-700"
    @click.stop="$emit('click')"
  >
    {{ label }}
  </button>
</template>

<script setup lang="ts">
interface Props {
  label: string
}

withDefaults(defineProps<Props>(), {})

defineEmits<{
  (event: 'click'): void
}>()
</script>
