package com.example.findamate.manager;

import com.example.findamate.domain.History;
import com.example.findamate.domain.Response;
import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.AsyncJob;
import com.example.findamate.helper.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ApiManager {
    private static final String HOST = "http://34.105.126.228:8000";
    private static int memberId;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void signup(String id, String password, SignupCallback callback) {
        request(String.format("%s/%s?loginId=%s&password=%s", HOST, "signup", id, password), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<Response>() {}).isSuccess());
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void login(String loginId, String password, LoginCallback callback) {
        request(String.format("%s/%s?loginId=%s&password=%s", HOST, "login", loginId, password), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    Response response = objectMapper.readValue(json, new TypeReference<Response>() {});
                    memberId = response.getMemberId();
                    callback.success(response.isSuccess());
                    Logger.debug("memberId: " + memberId);
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void getSchool(SchoolCallback callback) {
        request(String.format("%s/%s?memberId=%d", HOST, "school", memberId), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<School>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void updateSchool(School school) {
        request(String.format("%s/%s?memberId=%d&name=%s&year=%s&number=%s", HOST, "school/update", memberId,
                school.getName(), school.getYear(), school.getNumber()), new JsonCallback() {
            @Override
            public void success(String json) {}
        });
    }

    public static void getStudents(StudentListCallback callback) {
        request(String.format("%s/%s?memberId=%d", HOST, "student", memberId), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<List<Student>>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void addStudent(Student student, StudentCallback callback) {
        request(String.format("%s/%s?memberId=%d&name=%s&male=%s&phone=%s&avatarId=%d&score=%d&happiness=%d&message=%s",
                HOST, "student/add", memberId, student.getName(), (student.isMale() ? "true" : "false"), student.getPhone(),
                student.getAvatarId(), student.getScore(), -1, student.getStatusMessage()), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<Student>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void modifyStudent(Student student, StudentCallback callback) {
        request(String.format("%s/%s?id=%d&name=%s&male=%s&phone=%s&avatarId=%d&score=%d&happiness=%d&statusMessage=%s",
                HOST, "student/modify", student.getId(), student.getName(), (student.isMale() ? "true" : "false"), student.getPhone(),
                student.getAvatarId(), student.getScore(), -1, student.getStatusMessage()), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    if(callback != null) callback.success(objectMapper.readValue(json, new TypeReference<Student>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void deleteStudent(Student student) {
        request(String.format("%s/%s?id=%d", HOST, "student/delete", student.getId()), new JsonCallback() {
            @Override
            public void success(String json) {}
        });
    }

    public static void addMate(Student student, Student mate, int roundId) {
        request(String.format("%s/%s?memberId=%d&studentId=%d&mateId=%d&roundId=%d", HOST, "mate/add",
                memberId, student.getId(), (mate == null ? -1 : mate.getId()), roundId), new JsonCallback() {
            @Override
            public void success(String json) {
                Logger.debug(json);
            }
        });
    }

    public static void addFavoritePartner(Student student, Student mate, int rank) {
        request(String.format("%s/%s?memberId=&studentId=&mateId=&rank=", HOST, "favorite/add",
                memberId, student.getId(), mate.getId(), rank), new JsonCallback() {
            @Override
            public void success(String json) {
                Logger.debug(json);
            }
        });
    }

    public static void getRounds(RoundListCallback callback) {
        request(String.format("%s/%s?memberId=%d", HOST, "round", memberId), new JsonCallback() {
            @Override
            public void success(String json) {
                Logger.debug("round: " + json);
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<List<History>>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void addRound(AddRoundCallback callback) {
        request(String.format("%s/%s?memberId=%d", HOST, "round/add", memberId), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<History>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    private static void request(String url, JsonCallback callback) {
        new AsyncJob<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return request(strings[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                callback.success(result);
            }
        }.execute(url);
    }

    private static String request(String uri) {
        String result = "";

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            while(true) {
                String line = reader.readLine();
                if(line == null) break;
                result += line;
            }

            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            Logger.debug(e.getMessage());
        } catch (IOException e) {
            Logger.debug(e.getMessage());
        }

        return result;
    }

    public static int getMemberId() {
        return memberId;
    }

    public static void setMemberId(int memberId) {
        ApiManager.memberId = memberId;
    }

    private interface JsonCallback {
        void success(String json);
    }

    public interface SignupCallback {
        void success(boolean success);
    }

    public interface LoginCallback {
        void success(boolean success);
    }

    public interface SchoolCallback {
        void success(School school);
    }

    public interface StudentListCallback {
        void success(List<Student> students);
    }

    public interface StudentCallback {
        void success(Student student);
    }

    public interface RoundListCallback {
        void success(List<History> histories);
    }

    public interface AddRoundCallback {
        void success(History history);
    }

    /*class ResponseCode {
        private boolean seccess;

        public ResponseCode() {
        }

        public boolean isSeccess() {
            return seccess;
        }

        public void setSeccess(boolean seccess) {
            this.seccess = seccess;
        }
    }*/
}