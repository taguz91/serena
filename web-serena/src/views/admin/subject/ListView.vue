<template>
  <NPageHeader title="Materias" subtitle="Revisar, crear, editar y eliminar materias">
    <template #extra>
      <NSpace>
        <NButton type="primary" @click="showModal">
          <template #icon>
            <NIcon>
              <Plus />
            </NIcon>
          </template>

          Crear nuevo
        </NButton>
      </NSpace>
    </template>

    <NDataTable :columns="columns" :data="data" pagination />
  </NPageHeader>

  <FormView v-model="show" @update:model-value="(value: boolean) => (show = value)" />
</template>

<script setup lang="ts">
import { Pencil, Plus, Trash } from '@vicons/tabler'
import { NButton, NDataTable, NIcon, NPageHeader, NSpace, type DataTableColumns } from 'naive-ui'
import { h, onMounted, ref } from 'vue'
import FormView from './FormView.vue'

const show = ref(false)

const showModal = () => {
  show.value = true
}

interface Subject {
  name: string
}

const data = ref<Subject[]>([])

const columns: DataTableColumns<Subject> = [
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Acciones',
    key: 'actions',
    width: 125,
    className: 'flex justify-between',
    render: (row) => {
      return [
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              console.log('Edit', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Pencil) })
          }
        ),
        h(
          NButton,
          {
            type: 'error',
            tertiary: true,
            onClick: () => {
              console.log('Delete', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Trash) })
          }
        )
      ]
    }
  }
]

onMounted(() => {
  setTimeout(() => {
    data.value = [
      {
        name: 'Matemáticas'
      },
      {
        name: 'Lenguaje'
      },
      {
        name: 'Ciencias'
      },
      {
        name: 'Historia'
      },
      {
        name: 'Geografía'
      },
      {
        name: 'Educación Física'
      },
      {
        name: 'Artes'
      },
      {
        name: 'Música'
      },
      {
        name: 'Religión'
      },
      {
        name: 'Inglés'
      }
    ]
  }, 1000)
})
</script>
