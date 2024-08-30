<template>
  <NPageHeader title="Registros" subtitle="Revisar todos los registros creados">
    <NDataTable :columns="columns" :data="data" pagination />
  </NPageHeader>
</template>

<script setup lang="ts">
import { Eye } from '@vicons/tabler'
import { NButton, NDataTable, NIcon, NPageHeader, type DataTableColumns } from 'naive-ui'
import { h, onMounted, ref } from 'vue'

interface Register {
  date: string
  subject: string
  teacher: string
}

const data = ref<Register[]>([])

const columns: DataTableColumns<Register> = [
  {
    title: 'Subject',
    key: 'subject'
  },
  {
    title: 'Teacher',
    key: 'teacher'
  },
  {
    title: 'Date',
    key: 'date'
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
        date: '2021-10-10',
        subject: 'Math',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-11',
        subject: 'Science',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-12',
        subject: 'History',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-13',
        subject: 'Math',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-14',
        subject: 'Science',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-15',
        subject: 'History',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-16',
        subject: 'Math',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-17',
        subject: 'Science',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-18',
        subject: 'History',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-19',
        subject: 'Math',
        teacher: 'Jane Doe'
      }
    ]
  }, 1000)
})
</script>
