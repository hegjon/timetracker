Name: jonny-time
Version: 1.0
Release: 1%{?dist}
Summary: Jonny time tracker

License: Not decided
URL: http://jonnyware.com

Source0: %{name}.tar.gz
BuildArch: noarch

BuildRequires: java-devel >= 1:1.5.0
BuildRequires: maven-local
Requires:      java-headless >= 1:1.5.0

BuildRequires: joda-time
BuildRequires: apache-commons-lang3
BuildRequires: snakeyaml
BuildRequires: junit >= 4
Requires:      apache-commons-codec

%description
Hmm..

%package javadoc
Summary: Javadoc for %{name}

%description javadoc
API documentation for %{name}.

%prep

%setup -c

%build
%mvn_build

%install
%mvn_install

%jpackage_script com.jonnyware.timetracker.Main "" "" jonny-time:joda-time:apache-commons-lang3:snakeyaml:apache-commons-codec jtime true

%files -f .mfiles
/usr/bin/jtime

%files javadoc -f .mfiles-javadoc

%changelog
* Tue Mar 25 2014 Jonny Heggheim <hegjon@gmail.com> - 1.0-1
- Initial packaging
