<template>
  <NModal
    v-model:show="show"
    :mask-closable="false"
    preset="dialog"
    class="w-[500px]"
    title="Creación de una nueva materia"
    positive-text="Crear"
    negative-text="Cancelar"
    @positive-click="onPositiveClick"
    @negative-click="onNegativeClick"
  >
    <template #icon>
      <NIcon class="text-slate-800">
        <LayoutBoard />
      </NIcon>
    </template>

    <SmallSpinner v-if="isLoading" />

    <NForm v-else ref="formRef" class="mt-6" :model="model">
      <NFormItem label="Nombre" required>
        <NInput v-model:value="model.name" placeholder="Calculo" />
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { NForm, NFormItem, NIcon, NInput, NModal, type FormInst } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { LayoutBoard } from '@vicons/tabler'
import { ref, toRef } from 'vue'
import { useSubject } from '@/composable/subjects/useSubject'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'

interface Props {
  id?: string
  modelValue: boolean
}

const props = defineProps<Props>()

const id = toRef(props, 'id')
const emit = defineEmits<{
  (event: 'update:modelValue', value: boolean): void
}>()

const formRef = ref<FormInst | null>(null)
const { isLoading, save, subjectForm: model } = useSubject(id)

const show = useVModel(props, 'modelValue', emit)

const onPositiveClick = () => {
  save(formRef.value)
  show.value = false
  emit('update:modelValue', false)
}

const onNegativeClick = () => {
  show.value = false
  emit('update:modelValue', false)
}
</script>
