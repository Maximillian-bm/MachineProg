cmake_minimum_required(VERSION 3.27)
project(MachineProgOffLine)

set(CMAKE_C_STANDARD 11)

add_executable(MachineProg2 main.c)

target_link_libraries(MachineProg2 Ws2_32)