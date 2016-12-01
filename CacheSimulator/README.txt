Jon Karyo
jkaryo1
HW6

Problem 1:
Test 1: matrix[i][j] time
________________________________________________________
 Size | Opti | User Time | System Time   | Elapsed Time
------+------+-----------+---------------+-------------
 2048 |  o0  |   0.021   |     0.018     |   0:00.04
      |      |   0.027   |     0.011     |   0:00.04
      |  o1  |   0.003   |     0.014     |   0:00.02
      |      |   0.004   |     0.013     |   0:00.01
      |  o2  |   0.007   |     0.011     |   0:00.02
      |      |   0.005   |     0.012     |   0:00.01
      |  o3  |   0.003   |     0.007     |   0:00.01
      |      |   0.000   |     0.016     |   0:00.01
      |  o4  |   0.001   |     0.014     |   0:00.01
      |      |   0.001   |     0.015     |   0:00.01
------+------+-----------+---------------+-------------
 4096 |  o0  |   0.081   |     0.040     |   0:00.12
      |      |   0.079   |     0.042     |   0:00.12
      |  o1  |   0.016   |     0.047     |   0:00.06
      |      |   0.020   |     0.043     |   0:00.06
      |  o2  |   0.016   |     0.046     |   0:00.06
      |      |   0.013   |     0.052     |   0:00.06
      |  o3  |   0.007   |     0.049     |   0:00.05
      |      |   0.008   |     0.049     |   0:00.05
      |  o4  |   0.014   |     0.040     |   0:00.05
      |      |   0.009   |     0.047     |   0:00.05
------+------+-----------+---------------+-------------
 8192 |  o0  |   0.260   |     0.128     |   0:00.39
      |      |   0.257   |     0.142     |   0:00.40
      |  o1  |   0.050   |     0.138     |   0:00.19
      |      |   0.049   |     0.147     |   0:00.19
      |  o2  |   0.052   |     0.109     |   0:00.16
      |      |   0.047   |     0.152     |   0:00.20
      |  o3  |   0.016   |     0.134     |   0:00.15
      |      |   0.021   |     0.147     |   0:00.16
      |  o4  |   0.017   |     0.118     |   0:00.13
      |      |   0.021   |     0.134     |   0:00.15
------+------+-----------+---------------+-------------
16384 |  o0  |   0.964   |     0.528     |   0:01.49
      |      |   0.915   |     0.519     |   0:01.43
      |  o1  |   0.172   |     0.468     |   0:00.65
      |      |   0.143   |     0.534     |   0:00.67
      |  o2  |   0.165   |     0.502     |   0:00.66
      |      |   0.161   |     0.517     |   0:00.67
      |  o3  |   0.075   |     0.516     |   0:00.59
      |      |   0.069   |     0.528     |   0:00.59
      |  o4  |   0.089   |     0.523     |   0:00.61
      |      |   0.079   |     0.515     |   0:00.59

These results indicate that as matrix size doubles, the user time quadruples.
User time is the amount of time spent on the processor running the program's
code, so this is the important number to look at. Further, as optimization
increases to either O1 or O3, user time significantly decreases.

Test 2: matrix[j][i] time
________________________________________________________
 Size | Opti | User Time | System Time   | Elapsed Time
------+------+-----------+---------------+-------------
 2048 |  o0  |   0.112   |     0.012     |   0:00.12
      |      |   0.111   |     0.012     |   0:00.12
      |  o1  |   0.063   |     0.012     |   0:00.07
      |      |   0.063   |     0.013     |   0:00.07
      |  o2  |   0.063   |     0.014     |   0:00.07
      |      |   0.043   |     0.010     |   0:00.05
      |  o3  |   0.021   |     0.012     |   0:00.03
      |      |   0.019   |     0.013     |   0:00.03
      |  o4  |   0.022   |     0.012     |   0:00.03
      |      |   0.018   |     0.015     |   0:00.03
------+------+-----------+---------------+-------------
 4096 |  o0  |   1.124   |     0.039     |   0:01.16
      |      |   1.106   |     0.042     |   0:01.15
      |  o1  |   0.267   |     0.038     |   0:00.30
      |      |   0.274   |     0.035     |   0:00.31
      |  o2  |   0.266   |     0.038     |   0:00.30
      |      |   0.277   |     0.039     |   0:00.31
      |  o3  |   0.082   |     0.040     |   0:00.12
      |      |   0.089   |     0.051     |   0:00.14
      |  o4  |   0.082   |     0.043     |   0:00.12
      |      |   0.082   |     0.041     |   0:00.12
------+------+-----------+---------------+-------------
 8192 |  o0  |   6.848   |     0.142     |   0:06.99
      |      |   6.784   |     0.147     |   0:06.93
      |  o1  |   1.416   |     0.130     |   0:01.54
      |      |   1.426   |     0.138     |   0:01.56
      |  o2  |   1.378   |     0.127     |   0:01.50
      |      |   1.420   |     0.139     |   0:01.56
      |  o3  |   0.384   |     0.139     |   0:00.52
      |      |   0.374   |     0.131     |   0:00.50
      |  o4  |   0.368   |     0.128     |   0:00.49
      |      |   0.372   |     0.138     |   0:00.51
------+------+-----------+---------------+-------------
16384 |  o0  |  32.626   |     0.706     |   0:33.33
      |      |  27.869   |     0.566     |   0:28.43
      |  o1  |   5.854   |     0.603     |   0:06.45
      |      |   5.709   |     0.539     |   0:06.24
      |  o2  |   5.852   |     0.568     |   0:06.42
      |      |   5.620   |     0.536     |   0:06.15
      |  o3  |   1.502   |     0.557     |   0:02.06
      |      |   1.489   |     0.519     |   0:02.00
      |  o4  |   1.523   |     0.549     |   0:02.07
      |      |   1.437   |     0.523     |   0:01.96

These results indicate that as matrix size doubles, the user time significantly
increases. Further, as optimization increases to either O1 or O3, user time
significantly decreases, a result that is very noticeable in the 8192 and 16384
cases. System time, however, is the exact same as in the matrix[i][j] case,
indicating that this is not an ideal data point to study.

Overall, switching the i and j significantly increases run time, but not to a
constant proportion throughout all sizes. This causes me to believe that cache
performance impacts this.

Test 3: matrix[i][j] valgrind
MR = miss rate in %
Note: As size doubles, total refs quadruples
Note: o1 and o3 significantly decrease all refs (1/4 of what it was before)
_______________________________________________________
 Size | Opti | I1 MR | LLi MR | D1 MR | LLd MR | LL MR
------+------+-------+--------+-------+--------+-------
  512 |  o0  |  0.02 |  0.01  |  1.3  |   1.3  |  0.5
      |  o1  |  0.06 |  0.02  |  6.0  |   5.9  |  1.3
      |  o2  |  0.06 |  0.02  |  6.0  |   5.9  |  1.3
      |  o3  |  0.18 |  0.06  | 17.2  |  16.9  |  4.0
      |  o4  |  0.18 |  0.06  | 17.2  |  16.9  |  4.0
------+------+-------+--------+-------+--------+-------
 1024 |  o0  |  0.01 |  0.01  |  1.3  |   1.3  |  0.4
      |  o1  |  0.02 |  0.02  |  6.2  |   6.2  |  1.3
      |  o2  |  0.02 |  0.02  |  6.2  |   6.2  |  1.3
      |  o3  |  0.06 |  0.06  | 22.3  |  22.2  |  4.7
      |  o4  |  0.06 |  0.06  | 22.3  |  22.2  |  4.7
------+------+-----------+---------------+-------------
 2048 |  o0  |  0.00 |  0.00  |  1.3  |   1.3  |  0.4
      |  o1  |  0.00 |  0.00  |  6.2  |   6.2  |  1.3
      |  o2  |  0.00 |  0.00  |  6.2  |   6.2  |  1.3
      |  o3  |  0.02 |  0.02  | 24.2  |  24.2  |  4.9
      |  o4  |  0.02 |  0.02  | 24.2  |  24.2  |  4.9
------+------+-----------+---------------+-------------
 4096 |  o0  |  0.00 |  0.00  |  1.3  |   1.3  |  0.4
      |  o1  |  0.00 |  0.00  |  6.2  |   6.2  |  1.3
      |  o2  |  0.00 |  0.00  |  6.2  |   6.2  |  1.3
      |  o3  |  0.00 |  0.00  | 24.8  |  24.8  |  5.0
      |  o4  |  0.00 |  0.00  | 24.8  |  24.8  |  5.0
------+------+-----------+---------------+-------------
 8192 |  o0  |  0.00 |  0.00  |  1.3  |   1.3  |  0.4
      |  o1  |  0.00 |  0.00  |  6.2  |   6.2  |  1.3
      |  o2  |  0.00 |  0.00  |  6.2  |   6.2  |  1.3
      |  o3  |  0.00 |  0.00  | 25.0  |  24.9  |  5.0
      |  o4  |  0.00 |  0.00  | 25.0  |  24.9  |  5.0
------+------+-----------+---------------+-------------
16384 does not run with valgrind's cachegrind

An interesting result is that with no optimization, the miss rates stay constant
as size increases. However, miss rates increase as optimization increases. As
miss rates increase, runtime seems to decrease.

Test 4: matrix[j][i] valgrind
MR = miss rate in %
Note: As size doubles, total refs quadruples
Note: o1 and o3 significantly decrease all refs
_______________________________________________________
 Size | Opti | I1 MR | LLi MR | D1 MR | LLd MR | LL MR
------+------+-------+--------+-------+--------+-------
  512 |  o0  |  0.02 |  0.02  | 19.5  |   1.3  |  0.5
      |  o1  |  0.06 |  0.06  | 87.3  |   5.9  |  1.3
      |  o2  |  0.06 |  0.06  | 87.3  |   5.9  |  1.3
      |  o3  |  0.19 |  0.18  | 63.6  |  16.9  |  4.0
      |  o4  |  0.19 |  0.18  | 63.6  |  16.9  |  4.0
------+------+-------+--------+-------+--------+-------
 1024 |  o0  |  0.01 |  0.01  | 19.3  |   1.3  |  0.4
      |  o1  |  0.02 |  0.02  | 96.5  |   6.2  |  1.3
      |  o2  |  0.02 |  0.02  | 96.5  |   6.2  |  1.3
      |  o3  |  0.06 |  0.06  | 87.3  |  22.2  |  4.7
      |  o4  |  0.06 |  0.06  | 87.3  |  22.2  |  4.7
------+------+-------+--------+-------+--------+-------
 2048 |  o0  |  0.00 |  0.00  | 20.0  |  20.0  |  6.7
      |  o1  |  0.00 |  0.00  | 99.1  |  99.1  | 19.9
      |  o2  |  0.00 |  0.00  | 99.1  |  99.1  | 19.9
      |  o3  |  0.02 |  0.02  | 96.5  |  96.4  | 19.5
      |  o4  |  0.02 |  0.02  | 96.5  |  96.4  | 19.5
------+------+-----------+---------------+-------------
 4096 |  o0  |  0.00 |  0.00  | 20.0  |  20.0  |  6.7
      |  o1  |  0.00 |  0.00  | 99.8  |  99.8  | 20.0
      |  o2  |  0.00 |  0.00  | 99.8  |  99.8  | 20.0
      |  o3  |  0.00 |  0.00  | 99.1  |  99.1  | 19.9
      |  o4  |  0.00 |  0.00  | 99.1  |  99.1  | 19.9
------+------+-----------+---------------+-------------
 8192 |  o0  |  0.00 |  0.00  | 20.0  |  20.0  |  6.7
      |  o1  |  0.00 |  0.00  | 99.9  |  99.9  | 20.0
      |  o2  |  0.00 |  0.00  | 99.9  |  99.9  | 20.0
      |  o3  |  0.00 |  0.00  | 99.8  |  99.8  | 20.0
      |  o4  |  0.00 |  0.00  | 99.8  |  99.8  | 20.0
------+------+-----------+---------------+-------------
16384 does not run with valgrind's cachegrind

The miss rates in this test are very high due to the reverse ordering of i and
j. They are much higher than in test 3. The rates still tend to increase with
matrix size, but near the end miss a very large portion.

Overall, switching the i and j drastically changes performance. When this
happens, an entirely new array has to be loaded into the cache before each entry
into the matrix (since a matrix is an array of arrays). It gets to a point as
matrix size increases where there are so many misses that further increasing
size will have no significant effect. For small sizes, more arrays can exist in
the cache, leading to improved performance. For higher levels of optimization,
the miss rate is higher, but runtime is faster.


Problem 2:
Code is well documented.
