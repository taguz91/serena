<template>
  <NPageHeader title="Estudiantes" subtitle="Revisar todos los estudiantes">
    <NDataTable :columns="columns" :data="data" pagination />
  </NPageHeader>
</template>

<script setup lang="ts">
import { Eye } from '@vicons/tabler'
import { NButton, NDataTable, NIcon, NPageHeader, type DataTableColumns } from 'naive-ui'
import { h, onMounted, ref } from 'vue'

interface Subject {
  identification: string
  gender: string
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
    width: 100,
    className: 'flex justify-center',
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
            icon: () => h(NIcon, null, { default: () => h(Eye) })
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
        identification: '123456789',
        name: 'Juan Perez',
        gender: 'Masculino'
      },
      {
        identification: '987654321',
        name: 'Maria Rodriguez',
        gender: 'Femenino'
      },
      {
        identification: '456789123',
        name: 'Pedro Gomez',
        gender: 'Masculino'
      },
      {
        identification: '654321987',
        name: 'Ana Perez',
        gender: 'Femenino'
      }
    ]
  }, 1000)
})
</script>
