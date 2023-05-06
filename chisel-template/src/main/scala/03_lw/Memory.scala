package lw

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import common.Consts._

class ImemPortIo extends Bundle {
    val addr = Input(UInt(WORD_LEN.W))
    val inst = Output(UInt(WORD_LEN.W))
}

class DmemPortIo extends Bundle {
    val addr = Input(UInt(WORD_LEN.W))
    val rdata = Output(UInt(WORD_LEN.W))
}

class Memory extends Module {
    val io = IO(new Bundle {
        val imem = new ImemPortIo()
        val dmem = new DmemPortIo()
    })

    val mem = Mem(16384, UInt(8.W))
    loadMemoryFromFile(mem, "src/hex/lw.hex")
    val imem_addr = io.imem.addr
    io.imem.inst := Cat(
        mem(imem_addr + 3.U(WORD_LEN.W)),
        mem(imem_addr + 2.U(WORD_LEN.W)),
        mem(imem_addr + 1.U(WORD_LEN.W)),
        mem(imem_addr)
    )
    val dmem_addr = io.dmem.addr
    io.dmem.rdata := Cat(
        mem(dmem_addr + 3.U(WORD_LEN.W)),
        mem(dmem_addr + 2.U(WORD_LEN.W)),
        mem(dmem_addr + 1.U(WORD_LEN.W)),
        mem(dmem_addr)
    )
}
